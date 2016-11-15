package com.ampdev.platform.module.findbank.resource;

import com.ampdev.platform.framework.clock.ClockInstance;
import com.ampdev.platform.framework.dataaccess.IDataAccess;
import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;
import com.ampdev.platform.framework.rest.RestBaseResource;
import com.ampdev.platform.module.common.dataobject.ID;
import com.ampdev.platform.module.common.util.Util;
import com.ampdev.platform.module.findbank.dao.BankDao;
import com.ampdev.platform.module.findbank.dataobject.BankStatus;
import com.ampdev.platform.module.findbank.dataobject.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/ws/findbank")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BankResource extends RestBaseResource {

    private static final int FAST_CASH = 16;
    private static final int CASH = 90;
    private static final int NO_CASH = 360;

    private final static String SUCCESS = "{\"status\" : true}";
    private final static String SUCCESS_WITH_DATA = "{\"status\" : true, \"data\" : \"%s\"}";
    private final static String ERROR = "{\"status\": false, \"message\": \"%s\"}";
    @Autowired
    private BankDao bankDao;

    @Autowired
    private IDataAccess dataAccess;

    @RequestMapping(value = "/loc", method = RequestMethod.GET)
    public ResponseEntity<?> getBankByLocation(@RequestParam(value = "latX", required = true) String latX,
                                               @RequestParam(value = "latY", required = true) String latY) {

        BankStatus bankStatus = bankDao.findByLatXAndLatY(latX, latY);
        if (bankStatus == null) {
            bankStatus = new BankStatus();
            bankStatus.setAdded(false);
        } else {
            bankStatus.setAdded(true);
        }

        return new ResponseEntity<>(bankStatus, HttpStatus.OK);
    }

    @RequestMapping(value = "/ids", method = RequestMethod.POST)
    public ResponseEntity<?> getBankStatusByIds(RequestEntity<List<ID>> requestEntity) {

        final List<ID> mapIds = requestEntity.getBody();
        final List<String> ids = mapIds.stream().map(ID::getId).collect(Collectors.toList());
        List<BankStatus> objects = bankDao.findByMapIds(ids);
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createBank(RequestEntity<BankStatus> requestEntity) {
        BankStatus bankStatus = requestEntity.getBody();
        return save(bankStatus);
    }

    private ResponseEntity<?> save(BankStatus bankStatus) {
        if (bankStatus == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            //check if exist
            List<BankStatus> banks = bankDao.findByMapId(bankStatus.getMapId());
            if (Util.isEmpty(banks)) {
                dataAccess.create(bankStatus);
            } else {
                System.out.println(String.format("Bank with id %s at location (%s,%s) alread exists",
                        bankStatus.getId(), bankStatus.getLatX(), bankStatus.getLatY()));
            }
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/creates", method = RequestMethod.POST)
    public ResponseEntity<?> createBanks(RequestEntity<List<BankStatus>> requestEntity) {
        List<BankStatus> bankStatuses = requestEntity.getBody();
        for (BankStatus bankStatus : bankStatuses) {
            save(bankStatus);
        }
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBank(RequestEntity<BankStatus> requestEntity) {
        final BankStatus bankStatus = requestEntity.getBody();
        if (bankStatus == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final List<BankStatus> banks = bankDao.findByMapId(bankStatus.getMapId());
            if (!Util.isEmpty(banks)) {
                BankStatus bankStatusFromDB = banks.get(0);
                bankStatus.setId(bankStatusFromDB.getId());
                dataAccess.update(bankStatus);
                return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(String.format(ERROR, "Entity doesn't exist"), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(String.format(ERROR, "Error in updating"), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/update2", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBankSelective(RequestEntity<BankStatus> requestEntity) {
        final BankStatus bankStatus = requestEntity.getBody();
        if (bankStatus == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final List<BankStatus> banks = bankDao.findByMapId(bankStatus.getMapId());
            if (Util.isEmpty(banks)) {
                return new ResponseEntity<>(String.format(ERROR, "Entity doesn't exist"), HttpStatus.OK);
            } else {
                final BankStatus bankStatusFromDB = banks.get(0);
                if (bankStatusFromDB != null) {
                    bankStatusFromDB.setCashAvailable(bankStatus.getCashAvailable());
                    if (bankStatus.getCashAvailable() == 1) {
                        if (Util.takeAvg(bankStatusFromDB)) {
                            bankStatusFromDB.setAvgWaitTime((bankStatus.getAvgWaitTime()
                                    + bankStatusFromDB.getAvgWaitTime()) / 2);
                        } else {
                            bankStatusFromDB.setAvgWaitTime(bankStatus.getAvgWaitTime());
                        }
                    } else {
                        bankStatusFromDB.setNextAvailabilty(bankStatus.getNextAvailabilty());
                        bankStatusFromDB.setAvgWaitTime(-1L);
                    }
                    bankStatusFromDB.setWhenModified(new Date(ClockInstance.getInstance().getCurrentTimeMillis()));
                    dataAccess.update(bankStatusFromDB);
                    return new ResponseEntity<>(bankStatusFromDB, HttpStatus.OK);
                }
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(String.format(ERROR, "Error in updating"), HttpStatus.OK);
        }

        return new ResponseEntity<>(String.format(ERROR, "Entity doesn't exist"), HttpStatus.OK);
    }


    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ResponseEntity<?> feedback(RequestEntity<Feedback> requestEntity) {
        final Feedback feedback = requestEntity.getBody();
        if (feedback == null) {
            return new ResponseEntity<>(String.format(ERROR, "Feedback is null"), HttpStatus.OK);
        } else if (Util.isEmpty(feedback.getSubject())) {
            return new ResponseEntity<>(String.format(ERROR, "Subject can't be empty"), HttpStatus.OK);
        } else if (Util.isEmpty(feedback.getMessage())) {
            return new ResponseEntity<>(String.format(ERROR, "Subject can't be empty"), HttpStatus.OK);
        }

        // Crete feedback
        try {

            dataAccess.create("feedback", feedback);
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(String.format(ERROR, "Error in sending feedback"), HttpStatus.OK);
        }
    }
}
