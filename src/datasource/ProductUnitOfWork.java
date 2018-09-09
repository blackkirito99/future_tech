package datasource;


import java.util.ArrayList;

import domain.Product;

public class ProductUnitOfWork extends UnitOfWork<Product>{
  
	private static ThreadLocal<ProductUnitOfWork> current = new ThreadLocal<ProductUnitOfWork>();
	
	public static void newCurrent() {
		setCurrent(new ProductUnitOfWork());
	}

	public static void setCurrent(ProductUnitOfWork puow) {
		current.set(puow);
	}

	public static ProductUnitOfWork getCurrent() {
		return ((ProductUnitOfWork) current.get());
	}

	@Override
	public void commit() {
		for(Product object: newObjects) {
			ProductMapper.add(object);
		}
		for(Product object: dirtyObjects) {
			ProductMapper.update(object);
		}
		for(Product object: deletedObjects) {
			ProductMapper.delete(object);
		}
	    // clear new and deleted object in memory
	    newObjects = new ArrayList<>();
	    dirtyObjects = new ArrayList<>();
	    deletedObjects = new ArrayList<>();
	}
}
