package com.server.gymServerApplication.modelView.reques;


public record ChangeInforAccount( String Email,
                                  String Phone,
                                  byte[] avatar,
                                  String name
) {
}
