package de.janheyd.db.routing;

public class Location {

	public String id;
	public String name;

	public Location() {
	}

	public Location(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("Location[id=%s,name=%s]", id, name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Location location = (Location) o;

		if (id != null ? !id.equals(location.id) : location.id != null) return false;
		return name != null ? name.equals(location.name) : location.name == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
