package com.logiclegends.MerryMeal.service;

 

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

 

import org.springframework.stereotype.Service;

 

@Service
public class RateLimitingService {

 

    private Map<String, LoginAttempts> loginAttemptsMap = new ConcurrentHashMap<>();
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final int WAIT_PERIOD_SECONDS = 30;

 

    public boolean hasExceededMaxLoginAttempts(String username) {
        LoginAttempts loginAttempts = loginAttemptsMap.get(username);
        if (loginAttempts == null) {
            return false;
        }
        return loginAttempts.getCount() >= MAX_LOGIN_ATTEMPTS;
    }

 

    public void registerFailedLoginAttempt(String username) {
        LoginAttempts loginAttempts = loginAttemptsMap.get(username);
        if (loginAttempts == null) {
            loginAttempts = new LoginAttempts();
        }
        loginAttempts.incrementCount();
        loginAttempts.setLastFailedAttemptTimestamp(System.currentTimeMillis());
        loginAttemptsMap.put(username, loginAttempts);
//        loginAttemptsMap.remove(username);
    }

 

    public void resetLoginAttempts(String username) {
        loginAttemptsMap.remove(username);
    }

 

    
    public boolean canLoginAfterWaitPeriod(String username) {
        return getRemainingWaitTimeSeconds(username) == 0;
    }


    public long getRemainingWaitTimeSeconds(String username) {
        LoginAttempts loginAttempts = loginAttemptsMap.get(username);
        if (loginAttempts == null) {
            return 0;
        }
        long currentTime = System.currentTimeMillis();
        long lastFailedAttemptTimestamp = loginAttempts.getLastFailedAttemptTimestamp();
        long elapsedSeconds = (currentTime - lastFailedAttemptTimestamp) / 1000;
        return Math.max(0, WAIT_PERIOD_SECONDS - elapsedSeconds);
    }

 

    private static class LoginAttempts {
        private int count;
        private long lastFailedAttemptTimestamp;

 

        public int getCount() {
            return count;
        }

 

        public void incrementCount() {
            count++;
        }

 

        public long getLastFailedAttemptTimestamp() {
            return lastFailedAttemptTimestamp;
        }

 

        public void setLastFailedAttemptTimestamp(long lastFailedAttemptTimestamp) {
            this.lastFailedAttemptTimestamp = lastFailedAttemptTimestamp;
        }
    }
}
