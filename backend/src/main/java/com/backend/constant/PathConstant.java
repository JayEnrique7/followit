package com.backend.constant;

public interface PathConstant {
    static final String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

    String URL_LOGIN = "/login";
    String URL_LOGOUT = "/api/logout";
    String URL_GET_ALL_MESSAGES = "/api/messages/{id}";
    String URL_POST_MESSAGE = "/api/messages/post";
    String URL_DELETE_MESSAGE = "/api/messages/delete/{id}";
    String URL_GET_PROFILE = "/api/profile/{id}";
    String URL_EDIT_PROFILE = "/api/user/profile/edit";
    String URL_FOLLOW_LIST = "/api/follow/list/{id}";
    String URL_FOLLOW_USER = "/api/follow/user/{id}";
    String URL_UNFOLLOW_USER = "/api/unfollow/user/{id}";
}
