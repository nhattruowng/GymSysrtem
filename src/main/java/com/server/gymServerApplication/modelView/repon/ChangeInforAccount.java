package com.server.gymServerApplication.modelView.repon;


public record ChangeInforAccount( String Email,
                                  String Phone,
                                  byte[] avatar,
                                  String name
) {
}
