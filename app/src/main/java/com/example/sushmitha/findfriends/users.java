package com.example.sushmitha.findfriends;


    public class users {
        private String email;
        private String UserName;
        private Double latitude;
        private Double longitude;
        private String last_activity;

        users(String email, String Username, Double latitude, Double longitude, String last_activity) {
            this.email = email;
            this.UserName = Username;
            this.latitude = latitude;
            this.longitude = longitude;
            this.last_activity = last_activity;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFull_name() {
            return UserName;
        }

        public void setFull_name(String full_name) {
            this.UserName = full_name;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getLast_active_time() {
            return last_activity;
        }

        public void setLast_active_time(String last_activity) {
            this.last_activity = last_activity;
        }
    }


