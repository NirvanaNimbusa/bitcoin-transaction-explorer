package com.yoghurt.crypto.transactions.client.util;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AppAsyncCallback<T> implements AsyncCallback<T> {
  @Override
  public void onFailure(final Throwable caught) {
    GWT.log(caught.getMessage());
    throw new RuntimeException(caught);
  }
}
