package com.example.project3;

/**
 * Enum class for campuses New Brunswick, Newark, and Camden
 * @author Palak Singh, Daniel Guan
 */

public enum Campus {
    NEW_BRUNSWICK,
    NEWARK,
    CAMDEN;

    /**
     * toString method to get the campus name
     * @return String campus name
     */
    @Override
    public String toString(){
        return name();
    }

}