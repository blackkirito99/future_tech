package distribution;


import datasource.ProductLockingMapper;
import datasource.Registry;
import domain.Computer;
import domain.Product;
import domain.Smartphone;

/*
 * Use for Assemeble Product into DTO 
 * and convert back from DTO
 * */
public class ProductAssembler {

    public ProductDTO writeDTO(Product product) {
        ProductDTO result = new ProductDTO();
        String category = product.getCategory();

        result.setProductID(product.getProductID());
        result.setName(product.getName());
        result.setBrand(product.getBrand());
        result.setCategory(category);
        result.setPrice(product.getPrice());
        result.setStockNumber(product.getStockNumber());
        result.setImage(product.getImage());
        if (category.equals(Computer.CategoryString)) {
            result.setCpu(((Computer) product).getCpu());
            result.setDiskVolume(((Computer) product).getDiskVolume());
        } else if (category.equals(Smartphone.CategoryString)) {
            result.setScreenSize(((Smartphone) product).getScreenSize());
        }
        return result;
    }

    public Product readDTO(ProductDTO dto) {
        Product result = ProductLockingMapper.getInstance().find(Integer.toString(dto.getProductID()));
        String category = dto.getCategory();
        if (result != null) {
            result.setName(dto.getName());
            result.setCategory(category);
            result.setPrice(dto.getPrice());
            result.setStockNumber(dto.getStockNumber());
            result.setImage(dto.getImage());
        }
        if (category.equals(Computer.CategoryString)) {
            if (result == null) {
                result = new Computer(dto.getProductID(), dto.getName(), category, dto.getPrice(),
                    dto.getStockNumber(), dto.getImage(), true);
                Registry.addProduct(result);
            }
            result.setBrand(dto.getBrand());
            ((Computer) result).setCpu(dto.getCpu());
            ((Computer) result).setDiskVolume(dto.getDiskVolume());

        } else if (category.equals(Smartphone.CategoryString)) {
            if (result == null) {
                result = new Smartphone(dto.getProductID(), dto.getName(), category, dto.getPrice(),
                    dto.getStockNumber(), dto.getImage(), true);
                Registry.addProduct(result);
            }
            result.setBrand(dto.getBrand());
            ((Smartphone) result).setScreenSize(dto.getScreenSize());

        } else {
            System.out.println("WRONG TYPE");
            result = null;
        }
        return result;
    }
}