package com.ampdev.platform.module.findbank.resource;

import com.ampdev.platform.framework.dataaccess.IDataAccess;
import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;
import com.ampdev.platform.framework.rest.RestBaseResource;
import com.ampdev.platform.module.findbank.dao.BankDao;
import com.ampdev.platform.module.findbank.dataobject.BankStatus;
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

@Controller
@RequestMapping(value = "/ws/findbank")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BankResource extends RestBaseResource {


    @Autowired
    private BankDao bankDao;

    @Autowired
    private IDataAccess dataAccess;

    @RequestMapping(value = "/bank/loc", method = RequestMethod.GET)
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

    @RequestMapping(value = "/bank", method = RequestMethod.POST)
    public ResponseEntity<?> createBank(RequestEntity<BankStatus> requestEntity) {
        BankStatus bankStatus = requestEntity.getBody();
        if (bankStatus == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            //check if exist
            BankStatus bankStatusDB = bankDao.findByLatXAndLatY(bankStatus.getLatX(), bankStatus.getLatY());
            if (bankStatusDB == null) {
                dataAccess.create(bankStatus);
            } else {
                bankStatus.setId(bankStatusDB.getId());
                dataAccess.update(bankStatus);
            }
            return new ResponseEntity<>(bankStatus, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/bank", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBank(RequestEntity<BankStatus> requestEntity) {
        final BankStatus bankStatus = requestEntity.getBody();
        if (bankStatus == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final BankStatus bankStatusFromDB = bankDao.findByLatXAndLatY(bankStatus.getLatX(), bankStatus.getLatY());
            if (bankStatusFromDB != null) {
                bankStatus.setId(bankStatusFromDB.getId());
                dataAccess.update(bankStatus);
                return new ResponseEntity<>(bankStatus, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(String.format("Entity with name %d at location (%s,%s) doesn't exist",
                        bankStatusFromDB.getName(), bankStatusFromDB.getLatX(), bankStatus.getLatY()),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(String.format("Error updating bank with name %d at location (%s,%s) doesn't exist",
                    bankStatus.getName(), bankStatus.getLatX(), bankStatus.getLatY()),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
