/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.webapp.samples;

import java.io.IOException;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PageVisits implements Serializable {

    public static final Logger LOG = LogManager.getLogger(com.microsoft.webapp.samples.PageVisits.class);

    private int pageViews = 0;

    /**
     * Create a PageVisits counter
     */
    public PageVisits() {
        LOG.info("=========================================");
        LOG.info("Page Visit Counter is being created");
        LOG.info("=========================================");
    }

    public void increment() {
        this.pageViews++;
    }

    public int getValue() {
        return this.pageViews;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        LOG.info("=========================================");
        LOG.info("Writing out Page Visit into output stream");
        LOG.info("Page Visit Counter = " + this.pageViews);
        LOG.info("=========================================");

        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        in.defaultReadObject();

        LOG.info("=========================================");
        LOG.info("Read Page Visit Counter from input stream");
        LOG.info("Page Visit Counter = " + pageViews);
        LOG.info("=========================================");

    }

}
