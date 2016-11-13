package com.ampdev.platform.module.common.factory;

import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.country.constants.CountryConstants;
import com.ampdev.platform.module.news.constants.NewsConstants;
import com.ampdev.platform.module.tictactoe.constants.TicTacToeConstants;


public class QueryFactory {

    public static String getQuery(Module moduleEnum) {
        String queryName = null;

        switch (moduleEnum) {
            case COUNTRY:
                queryName = CountryConstants.QUERY_GET_COUNTRIES;
                break;
            case NEWS:
                queryName = NewsConstants.QUERY_GET_NEWS;
                break;
            case TICTACTOE:
                queryName = TicTacToeConstants.QUERY_GET_CURRENT_GAMES;
                break;
            default:
                break;
        }
        return queryName;
    }

    public static String getAllQuery(Module moduleEnum) {
        String queryName = null;

        switch (moduleEnum) {
            case COUNTRY:
                queryName = CountryConstants.QUERY_GET_ALL_COUNTRIES;
                break;
            case NEWS:
                queryName = NewsConstants.QUERY_GET_ALL_NEWS;
                break;
            case TICTACTOE:
                queryName = TicTacToeConstants.QUERY_GET_ALL_CURRENT_GAMES;
            default:
                break;
        }
        return queryName;
    }

    public static String getDeleteQueery(Module moduleEnum) {
        String queryName = null;

        switch (moduleEnum) {
            case COUNTRY:
                queryName = CountryConstants.QUERY_DELTE_COUNTRY;
                break;
            case NEWS:
                queryName = NewsConstants.QUERY_DELTE_NEWS;
                break;
            case TICTACTOE:
                queryName = TicTacToeConstants.QUERY_DELTE_CURRENT_GAMES;
                break;

            default:
                break;
        }
        return queryName;
    }
}
