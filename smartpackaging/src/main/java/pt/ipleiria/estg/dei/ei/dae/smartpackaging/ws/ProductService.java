package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

@Path("products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductService {
    @EJB
    private ProductBean productBean;

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getExpireDate().toString(),
                product.getWeight(),
                product.getIngredients(),
                product.getSmartPackage().getId()
        );
    }

    private List<ProductDTO> toDTOs(List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProductDTO> getAllProducts() {
        return toDTOs(productBean.getAllProducts());
    }
}
