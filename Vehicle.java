package com.thekarlbrown;

import java.util.Calendar;

/**
 * Here is my custom made description of what a Vehicle entail, using theoretical classes Person and VehicleKey,
 * I extend the theoretical class Machine to reuse functions and implement theoretical RemoteControl functions
 * I included a CarKey, and made the Vehicle slightly more secure (being inside Vehicle still required key for protected functions)
 * Creating a Vehicle requires the characteristics of a Vehicle, and to legally drive you must register with the DMV
 * I created methods regarding legal use of the Vehicle, add drivers/passengers, fill up your tank, checking DMV status,
 * Included CarKey-secure functions, setters and getters are assigned, characteristics such as VIN number  cannot be altered
 */
public class Vehicle extends Machine implements RemoteControl.VehicleControl{
    //Characteristics of a Vehicle, all private as they are not to be modified outside methods for security
    private boolean engineState,doorsLocked,trunkOpen,gasFlapOpen,legalToDrive,isAStick;
    private Person owner,driver;
    private VehicleKey vehicleKey;
    private List<Person> passengers;
    private String vehicleRegistration,vehicleRegistrationState, licensePlateNumber, VINnumber,vehicleMake,vehicleModel,vehicleColor;
    private int vehicleYear,registrationExpirationYear,registrationExpirationMonth,seatsInVehicle;
    private double gasGauge,speed,tankSize;

    /**
     * Get methods for all accessible fields outside the class (you cannot see the key to acccess Vehicle)
     */
    public boolean getEngineState(){return engineState;}
    public boolean getDoorsLocked(){return doorsLocked;}
    public boolean getTrunkOpen(){return trunkOpen;}
    public boolean getGasFlapOpen(){return gasFlapOpen;}
    public boolean getLegalToDrive(){return legalToDrive;}
    public boolean getIsAStick(){return isAStick;}
    public Person getOwner(){return owner;}
    public Person getDriver(){return driver;}
    public List<Person> getPassengers(){return passengers;}
    public String getVehicleRegistration(){return vehicleRegistration;}
    public String getVehicleRegistrationState(){return vehicleRegistrationState;}
    public String getLicensePlateNumber(){return licensePlateNumber;}
    public String getVINnumber(){return VINnumber;}
    public String getVehicleMake(){return vehicleMake;}
    public String getVehicleModel(){return vehicleModel;}
    public String getVehicleColor(){return vehicleColor;}
    public int getVehicleYear(){return vehicleYear;}
    public int getRegistrationExpirationYear(){return registrationExpirationYear;}
    public int getRegistrationExpirationMonth(){return registrationExpirationMonth;}
    public int getSeatsInVehicle(){return seatsInVehicle;}
    public double getGasGauge(){return gasGauge;}
    public double getSpeed(){return speed;}
    public double getTankSize(){return tankSize;}

    /**
     * Create your Vehicle with required default characteristics
     * @param vehicleKey - The key assigned by the manufacture
     * @param seatsInVehicle - How many seats are in the Vehicle
     * @param VINnumber - Default VIN Number
     * @param vehicleMake - Make of your vehicle
     * @param vehicleModel - Model of your vehicle
     * @param vehicleColor - External color of your vehicle
     * @param vehicleYear - Year of your vehicle
     * @param isAStick - True for stick, false for automatic
     * @param tankSize - How many gallons does your tank hold?
     */
    public Vehicle(VehicleKey vehicleKey,String VINnumber,String vehicleMake,String vehicleModel,String vehicleColor,int vehicleYear, int seatsInVehicle, boolean isAStick,double tankSize){
        this.vehicleKey=vehicleKey;
        this.VINnumber=VINnumber;
        this.vehicleMake=vehicleMake;
        this.vehicleModel=vehicleModel;
        this.vehicleYear=vehicleYear;
        this.vehicleColor=vehicleColor;
        this.isAStick=isAStick;
        legalToDrive=false;
        engineState=false;
        passengers=new ArrayList<Person>();
        owner=null;
        driver=null;
        this.tankSize=tankSize;
        gasGauge=0;
        speed=0;
        licensePlateNumber="none";
        vehicleRegistration="none";
        vehicleRegistrationState="none";
        registrationExpirationYear=-1;
        this.doorsLocked=true;
        this.trunkOpen=false;
        this.gasFlapOpen=false;
        this.seatsInVehicle=seatsInVehicle;
    }

    /**
     * Register your Vehicle with the DMV
     * @param licensePlateNumber - Assigned license plate number
     * @param vehicleRegistration - Assigned vehicle registration number
     * @param vehicleRegistrationState - State vehicle is registered in
     */
    public void registerVehicle(String licensePlateNumber,String vehicleRegistration,String vehicleRegistrationState){
        this.licensePlateNumber=licensePlateNumber;
        this.vehicleRegistration=vehicleRegistration;
        this.vehicleRegistrationState=vehicleRegistrationState;
        Calendar now=Calendar.getInstance();
        this.registrationExpirationYear= now.get(Calendar.YEAR)+1;
        this.registrationExpirationMonth=now.get(Calendar.MONTH);
        legalToDrive=true;
    }

    /**
     * Verify the Vehicle key
     * @param key - Key attempted to be placed in lock
     * @return - If the key fits or not
     */
    public boolean vehicleKeyVerificaton (VehicleKey key){
        return vehicleKey.keyMatches(key);
    }

    /**
     * Turn the engine on or off
     * @param key - Key that is attempting to access the functions
     * @param state - State of the engine
     * @return Did we successfully access the engine state?
     */
    public boolean setEngineState(boolean state, VehicleKey key) {
        if (vehicleKeyVerificaton(key)) {
            engineState = state;
            return true;
        }
        return false;
    }

    /**
     * Lock or unlock the door, assumes you need the key to lock/unlock door
     * @param key - Key that is attempting to access the functions
     * @param state - State of the door
     * @return Did we successfully access the door state?
     */
    public boolean setDoorState(boolean state, VehicleKey key){
        if(vehicleKeyVerificaton(key)){
            doorsLocked=state;
            return true;
        }
        return false;
    }

    /**
     * Open or close the trunk, assumes you need the key to open/close trunk
     * @param key - Key that is attempting to access the functions
     * @param state - State of the trunk
     * @return Did we successfully access the trunk state?
     */
    public boolean setTrunkOpen(boolean state, VehicleKey key){
        if(vehicleKeyVerificaton(key)){
            trunkOpen=state;
            return true;
        }
        return false;
    }

    /**
     * Open or closing gas flap, assuming you can change gas Flap outside Vehicle/without key
     * @param state - State of the gas flap
     */
    public void setGasFlapOpen(boolean state){gasFlapOpen=state;}

    /**
     * Checks the registration compared with the current date setting accordingly
     */
    public void checkRegistration(){
        Calendar now=Calendar.getInstance();
        if((now.get(Calendar.YEAR)>=registrationExpirationYear)&&(now.get(Calendar.MONTH)>registrationExpirationMonth)){
            legalToDrive=false;
        }
    }
    /**
     * Remote control the engine
     * Over-rides from RemoteControl implementation
     * @param engineState - Turn the engine on or off
     */
    @Override
    public void remoteControlEngine (boolean engineState) { this.engineState=engineState; }

    /**
     * Lock/Unlock the door remotely
     * Over-rides from RemoteControl implementation
     * @param lockState - True if unlocked, false if locked
     */
    @Override
    public void remoteDoorLockState (boolean lockState) { doorsLocked=lockState; }

    /**
     * Open/close the trunk remotely
     * Over-rides from RemoteControl implementation
     * @param trunkState - True if open, false if closed
     */
    @Override
    public void remoteTrunkState (boolean trunkState){ trunkOpen=trunkState; }

    /**
     * See how much room is left in the tank
     * @return How much room in gallons is left for gas
     */
    public double maxGasToFillUpTank(){ return tankSize-gasGauge; }

    /**
     * Adds gas to tank if possible
     * @param gasToAdd - How much gas should be added
     * @return If gas was added or not
     */
    public boolean addGasToTank(double gasToAdd){
        if(gasFlapOpen&&((gasToAdd+gasGauge)<=maxGasToFillUpTank())){
            gasGauge=gasGauge+gasToAdd;
            return true;
        }
        return false;
    }

    /**
     * See if we need to get gas (warning at 5% of max tank size
     * @return True if we need gas, false if we are 6% or higher
     */
    public boolean gasTankCloseToEmpty(){
        if((gasGauge/tankSize)<0.06){
            return true;
        }
        return false;
    }

    /**
     * Transfer the vehicle owner
     * @param newOwner - New vehicle owner
     */
    public void transferOwner(Person newOwner){
        owner=newOwner;
        legalToDrive=false;
    }

    /**
     * Checks to see if the Person can drive a stick if necessary
     * @param person - Person in question
     * @return - If they are capable to drive the Vehicle
     */
    public boolean canDriveVehicle(Person person){
        if(isAStick&&!person.canDriveStick()){  return false; }
        return true;
    }

    /**
     * Illustration of potential smart recognition for legal driving
     * @return If you can use the Vehicle
     */
    public boolean goOnATrip(VehicleKey key)
    {
        if(legalToDrive&&vehicleKeyVerificaton(key)){
             engineState=true;
            return true;
        }
        return false;
    }

    /**
     * Sets driver in Vehicle as Person, adds them to Vehicle if empty and there is enough room
     * @param person - Person in consideration for driver
     * @return True if successful, false if condition violated
     */
    public boolean setDriverInVehicle(Person person){
        if(!person.hasLicenseAndInsurance()||!canDriveVehicle(person)||doorsLocked){
            return false;
        }
        if(passengers.contains(person)){
            driver=person;
        }else if(passengers.size()<seatsInVehicle){
            return false;
        }else{
            passengers.add(person);
        }
        return true;
    }
    /**
     * Method to add a Person to the  who is not currently driving
     * @param person - Person to be added
     * @return If the Person can be added
     */
    public boolean addPersonToVehicle(Person person){
        if(!passengers.contains(person)&&(passengers.size()<seatsInVehicle)&&!doorsLocked){
            passengers.add(person);
            return true;
        }
        return false;
    }
}
