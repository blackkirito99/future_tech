package datasource;

import java.util.ArrayList;
import java.util.List;

/*abstract class for different unit of work*/
public abstract class UnitOfWork<T> {

	protected List<T> newObjects = new ArrayList<>();
	protected List<T> dirtyObjects = new ArrayList<>();
	protected List<T> deletedObjects = new ArrayList<>();

	public void registerNewObject(T object) {
		if(object == null) {
			System.out.println("object is null");
			return;
		}
		if(dirtyObjects.contains(object)) {
			System.out.println("object is dirty");
			return;
		}
		if(deletedObjects.contains(object)) {
			System.out.println("objec is deleted");
			return;
		}
		if(newObjects.contains(object)) {
			System.out.println(object.toString() + " is new");
			return;
		}
		newObjects.add(object);
		System.out.println(object.toString() + " is new object!");
	}

	public void registerDirtyObject(T object) {
		if(object == null) {
			System.out.println("object is null");
			return;
		}
		if(dirtyObjects.contains(object)) {
			System.out.println(object.toString() + " is dirty");
			return;
		}
		if(deletedObjects.contains(object)) {
			System.out.println(object.toString() + " deleted");
			return;
		}
		if(!dirtyObjects.contains(object) && !newObjects.contains(object)) {
			dirtyObjects.add(object);
		}
		System.out.println(object.toString() + " becomes dirty!");
	}

	public void registerDeletedObject(T object) {
		if(object == null) {
			System.out.println("object is null");
			return;
		}
		if(newObjects.remove(object)) {
		  return;
		}
		dirtyObjects.remove(object);
		if(!deletedObjects.contains(object)) {
			deletedObjects.add(object);
		}
		System.out.println(object.toString() + " is going to be deleted!");
	}

	public void registerCleanObject(T object) {
		if(object == null) System.out.println("object is null");
		//System.out.println(object.toString() + " is still clean!");
	}

	public abstract void commit();
	
}
