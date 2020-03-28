/*
 * Copyright (c) Serhan CANOVA - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Created and written by Serhan CANOVA <serhancanova@gmail.com>, 6.3.2020
 */

package com.sba.covid.acil.api.core;

public interface ResponseInterface {

    void success(Object obj);

    void error(Object obj);
}
