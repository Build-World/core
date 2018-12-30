package com.buildworld.mods.core.blocks.properties;

import com.buildworld.game.blocks.properties.IBlockProperty;
import com.buildworld.game.blocks.types.IBlockType;
import com.buildworld.mods.core.blocks.types.Electric;

import java.util.ArrayList;

public class Electricity implements IBlockProperty {
    
    private float inputVoltage = 0f;
    private float maxvoltage = 120f;
    private float minvoltage = 0f;

    private float amperage = 0f;
    private float maxamperage = 1f;
    private float minamperage = 0f;

    private float resistance = 0f;
    private float capacitance = 0f;
    private float inductance = 0f;

    private boolean producer = false;

    public Electricity() {}

    @Override
    public String getDescription() {
        return "Electricity";
    }

    @Override
    public IBlockProperty make() {
        return new Electricity();
    }

    @Override
    public String getKey() {
        return "electricity";
    }

    @Override
    public String getName() {
        return "Electricity";
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public boolean isTypesWhitelisted() {
        return true;
    }

    @Override
    public ArrayList<IBlockType> getWhitelist() {
        return new ArrayList<>(){{add(new Electric());}};
    }

    public float getInputVoltage() {
        return inputVoltage;
    }

    public void setInputVoltage(float inputVoltage) {
        this.inputVoltage = inputVoltage;
    }

    public float getMaxvoltage() {
        return maxvoltage;
    }

    public void setMaxvoltage(float maxvoltage) {
        this.maxvoltage = maxvoltage;
    }

    public float getMinvoltage() {
        return minvoltage;
    }

    public void setMinvoltage(float minvoltage) {
        this.minvoltage = minvoltage;
    }

    public float getAmperage() {
        return amperage;
    }

    public void setAmperage(float amperage) {
        this.amperage = amperage;
    }

    public float getMaxamperage() {
        return maxamperage;
    }

    public void setMaxamperage(float maxamperage) {
        this.maxamperage = maxamperage;
    }

    public float getMinamperage() {
        return minamperage;
    }

    public void setMinamperage(float minamperage) {
        this.minamperage = minamperage;
    }

    public float getResistance() {
        return resistance;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public float getCapacitance() {
        return capacitance;
    }

    public void setCapacitance(float capacitance) {
        this.capacitance = capacitance;
    }

    public float getInductance() {
        return inductance;
    }

    public void setInductance(float inductance) {
        this.inductance = inductance;
    }

    public boolean isProducer() {
        return producer;
    }

    public void setProducer(boolean producer) {
        this.producer = producer;
    }

    public boolean isVoltageNormal()
    {
        if(inputVoltage > maxvoltage || inputVoltage < minvoltage)
            return false;
        return true;
    }

    public boolean isCurrentNormal()
    {
        if(amperage > maxamperage || amperage < minamperage)
            return false;
        return true;
    }

    public float getPower()
    {
        return inputVoltage * amperage;
    }

    public float getVoltageDrop()
    {
        return amperage * resistance;
    }

    public float getOutputVoltage()
    {
        return inputVoltage - getVoltageDrop();
    }

    public boolean isSuperConductor()
    {
        return resistance == 0f;
    }
}
