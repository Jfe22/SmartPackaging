package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
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
                product.getIngredients()
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

    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") int id)
    throws MyEntityNotFoundException {
        Product product = productBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(product)).build();
    }

    @POST
    @Path("/")
    public Response createNewProduct (ProductDTO productDTO)
    throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        productBean.create(
                productDTO.getId(),
                productDTO.getName(),
                LocalDate.parse(productDTO.getDate()),
                productDTO.getWeight(),
                productDTO.getIngredients()
        );
        Product newProduct = productBean.find(productDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(newProduct)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateProduct (@PathParam("id") int id, ProductDTO productDTO)
    throws MyEntityNotFoundException, MyConstraintViolationException {
        productBean.update(
                id,
                productDTO.getName(),
                LocalDate.parse(productDTO.getDate()),
                productDTO.getWeight(),
                productDTO.getIngredients()
        );
        Product updatedProduct = productBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(updatedProduct)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") int id)
    throws MyEntityNotFoundException {
        productBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }
}
