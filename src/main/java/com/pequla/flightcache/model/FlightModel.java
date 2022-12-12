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
public class FlightModel {

    @JsonProperty("KLJUC")
    private String key;

    @JsonProperty("TIP")
    private String TIP;

    @JsonProperty("DAN")
    private String DAN;

    @JsonProperty("BROJ_LETA")
    private String BROJ_LETA;

    @JsonProperty("ST")
    private String ST;

    @JsonProperty("ET")
    private String ET;

    @JsonProperty("DESTINACIJA")
    private String DESTINACIJA;

    @JsonProperty("PREKO")
    private String PREKO;

    @JsonProperty("TIP_VEZE")
    private String TIP_VEZE;

    @JsonProperty("VEZAN_LET")
    private String VEZAN_LET;

    @JsonProperty("TIME")
    private String TIME;

    @JsonProperty("REMARK")
    private String REMARK;

    @JsonProperty("DATUM")
    private String DATUM;

    @JsonProperty("DATUM_E")
    private String DATUM_E;

    @JsonProperty("TIP_AVIONA")
    private String TIP_AVIONA;

    @JsonProperty("GATE_BAY")
    private String GATE_BAY;

    @JsonProperty("TERMINAL")
    private String TERMINAL;
}
