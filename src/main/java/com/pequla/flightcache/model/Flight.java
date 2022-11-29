package com.pequla.flightcache.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Flight {

    @JsonProperty("KLJUC")
    private String key;

    @JsonProperty("TIP")
    private String TIP;
    private String DAN;
    private String BROJ_LETA;
    private String ST;
    private String ET;
    private String DESTINACIJA;
    private String PREKO;
    private String TIP_VEZE;
    private String VEZAN_LET;
    private String TIME;
    private String REMARK;
    private String DATUM;
    private String DATUM_E;
    private String TIP_AVIONA;
    private String GATE_BAY;
    private String TERMINAL;
}
