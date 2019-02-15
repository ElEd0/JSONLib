package es.ed0.tinyjson.test;

import es.ed0.tinyjson.serialization.Ignore;

public class Student {

    private String id;
    private String name;
    private String year;
    private boolean alone = false;
    private int[] pickUpWeek, dropOffWeek;
    
    @Ignore
    private int mondayPickUpStop, tuesdayPickUpStop, wednesdayPickUpStop, thursdayPickUpStop, fridayPickUpStop,
            mondayDropOffStop, tuesdayDropOffStop, wednesdayDropOffStop, thursdayDropOffStop, fridayDropOffStop;


    @Ignore()
    public boolean checked = false;
    
    public Student() {
    	
    }

    public Student(String id, String name, String year, boolean alone, int[] dropOffWeek, int[] pickUpWeek) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.alone = alone;
        
        this.pickUpWeek = pickUpWeek;
        this.dropOffWeek = dropOffWeek;

        this.mondayDropOffStop = dropOffWeek[0];
        this.tuesdayDropOffStop = dropOffWeek[1];
        this.wednesdayDropOffStop = dropOffWeek[2];
        this.thursdayDropOffStop = dropOffWeek[3];
        this.fridayDropOffStop = dropOffWeek[4];

        this.mondayPickUpStop = pickUpWeek[0];
        this.tuesdayPickUpStop = pickUpWeek[1];
        this.wednesdayPickUpStop = pickUpWeek[2];
        this.thursdayPickUpStop = pickUpWeek[3];
        this.fridayPickUpStop = pickUpWeek[4];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isAlone() {
        return alone;
    }

    public void setAlone(boolean alone) {
        this.alone = alone;
    }
    
	public int[] getPickUpWeek() {
		return pickUpWeek;
	}

	public void setPickUpWeek(int[] pickUpWeek) {
		this.pickUpWeek = pickUpWeek;
	}

	public int[] getDropOffWeek() {
		return dropOffWeek;
	}

	public void setDropOffWeek(int[] dropOffWeek) {
		this.dropOffWeek = dropOffWeek;
	}

	public int getMondayPickUpStop() {
		return mondayPickUpStop;
	}

	public void setMondayPickUpStop(int mondayPickUpStop) {
		this.mondayPickUpStop = mondayPickUpStop;
	}

	public int getTuesdayPickUpStop() {
		return tuesdayPickUpStop;
	}

	public void setTuesdayPickUpStop(int tuesdayPickUpStop) {
		this.tuesdayPickUpStop = tuesdayPickUpStop;
	}

	public int getWednesdayPickUpStop() {
		return wednesdayPickUpStop;
	}

	public void setWednesdayPickUpStop(int wednesdayPickUpStop) {
		this.wednesdayPickUpStop = wednesdayPickUpStop;
	}

	public int getThursdayPickUpStop() {
		return thursdayPickUpStop;
	}

	public void setThursdayPickUpStop(int thursdayPickUpStop) {
		this.thursdayPickUpStop = thursdayPickUpStop;
	}

	public int getFridayPickUpStop() {
		return fridayPickUpStop;
	}

	public void setFridayPickUpStop(int fridayPickUpStop) {
		this.fridayPickUpStop = fridayPickUpStop;
	}

	public int getMondayDropOffStop() {
		return mondayDropOffStop;
	}

	public void setMondayDropOffStop(int mondayDropOffStop) {
		this.mondayDropOffStop = mondayDropOffStop;
	}

	public int getTuesdayDropOffStop() {
		return tuesdayDropOffStop;
	}

	public void setTuesdayDropOffStop(int tuesdayDropOffStop) {
		this.tuesdayDropOffStop = tuesdayDropOffStop;
	}

	public int getWednesdayDropOffStop() {
		return wednesdayDropOffStop;
	}

	public void setWednesdayDropOffStop(int wednesdayDropOffStop) {
		this.wednesdayDropOffStop = wednesdayDropOffStop;
	}

	public int getThursdayDropOffStop() {
		return thursdayDropOffStop;
	}

	public void setThursdayDropOffStop(int thursdayDropOffStop) {
		this.thursdayDropOffStop = thursdayDropOffStop;
	}

	public int getFridayDropOffStop() {
		return fridayDropOffStop;
	}

	public void setFridayDropOffStop(int fridayDropOffStop) {
		this.fridayDropOffStop = fridayDropOffStop;
	}

    
}
