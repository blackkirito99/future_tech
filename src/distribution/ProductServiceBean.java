package distribution;

import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import datasource.ProductLockingMapper;
import datasource.UnitOfWork;
import domain.Product;

public class ProductServiceBean {

    public ProductDTO getProduct(String id) throws RemoteException {
        return new ProductAssembler().writeDTO(ProductLockingMapper.getInstance().find(id));
    }
    public void getProductXml(String id, OutputStream outputStream) throws RemoteException {
        ProductDTO dto = getProduct(id);
        ProductDTO.toXML(dto, outputStream);
    }

    public List <ProductDTO> getAllProducts() throws RemoteException {
        List <Product> products = ProductLockingMapper.getInstance().findAll();
        ProductAssembler PA = new ProductAssembler();
        List <ProductDTO> dtos = new ArrayList <ProductDTO> ();
        for (Product p: products) {
            // Call one method to get object fully loaded
            // as in distributed system, we need to save 
            // time to transfer data
            p.getStockNumber();

            dtos.add(PA.writeDTO(p));
        }
        return dtos;
    }
    public void getAllProductsXml(OutputStream outputStream) throws RemoteException {
        for (ProductDTO dto: getAllProducts()) {
            ProductDTO.toXML(dto, outputStream);
        }
    }

    public void updateProduct(ProductDTO dto) throws RemoteException {
        UnitOfWork uuow = new UnitOfWork();
        // already refer to registry in assembler, so no need to further check
        new ProductAssembler().readDTO(dto);
        uuow.commit();
    }

    public void updateProduct(InputStream inputStream) throws RemoteException {
        ProductDTO dto = ProductDTO.fromXML(inputStream);
        updateProduct(dto);
    }

    public void deleteProduct(ProductDTO dto) throws RemoteException {
        UnitOfWork uow = new UnitOfWork();
        ProductLockingMapper.getInstance().delete(((new ProductAssembler()).readDTO(dto)));
        uow.registerDeletedObject((new ProductAssembler()).readDTO(dto));
        uow.commit();
    }

    public void deleteProduct(InputStream inputStream) throws RemoteException {
        ProductDTO dto = ProductDTO.fromXML(inputStream);
        deleteProduct(dto);
    }
    public void insertProduct(ProductDTO dto) throws RemoteException {
        UnitOfWork uuow = new UnitOfWork();
        // already refer to registry in assembler, so no need to further check
        new ProductAssembler().readDTO(dto);
        uuow.commit();
    }

    public void insertProduct(InputStream inputStream) throws RemoteException {
        ProductDTO dto = ProductDTO.fromXML(inputStream);
        insertProduct(dto);
    }
}