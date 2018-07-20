package com.bhm.sdk.greendao.demo.entity;

public final class GenerateEntity {
  private String firstName;
  private String lastName;
  /**
   * Return the person's full name
   */
  public String getName() {
    return firstName + " - " + lastName;
  }
  public String getFirstName() {
    return firstName;
  }
  public String getLastName() {
    return lastName;
  }
}
