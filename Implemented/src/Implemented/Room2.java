package Implemented;

import Interfaces.Room;

public class Room2 implements Room, Comparable<Room>{
	private final String bldg;
	private final String room;

	public Room2(String bldg, String room) {
		this.bldg = bldg;
		this.room = room;
	}

	@Override
	public String getBuilding() {
		return this.bldg;
	}

	@Override
	public String getRoomNumber() {
		return this.room;
	}

	@Override
	public int compareTo(Room o) {
		if(this.getBuilding()==o.getBuilding()) {
			return this.getRoomNumber().compareTo(o.getRoomNumber());
		}else {
			return this.getBuilding().compareTo(o.getBuilding());
		}
	}

}

