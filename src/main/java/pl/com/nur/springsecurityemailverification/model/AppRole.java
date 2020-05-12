package pl.com.nur.springsecurityemailverification.model;

public enum AppRole {
        USER,
        ADMIN;

        public String getName(){
                return this.name();
        }
}
