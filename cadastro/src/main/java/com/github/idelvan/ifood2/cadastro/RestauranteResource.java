package com.github.idelvan.ifood2.cadastro;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.idelvan.ifood2.cadastro.dto.AdicionarPratoDTO;
import com.github.idelvan.ifood2.cadastro.dto.AdicionarRestauranteDTO;
import com.github.idelvan.ifood2.cadastro.dto.AtualizarRestauranteDTO;
import com.github.idelvan.ifood2.cadastro.dto.PratoMapper;
import com.github.idelvan.ifood2.cadastro.dto.RestauranteDTO;
import com.github.idelvan.ifood2.cadastro.dto.RestauranteMapper;
import com.github.idelvan.ifood2.cadastro.infra.ConstraintViolationResponse;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/restaurantes")
@Tag(name = "Restaurante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("proprietario")

@SecurityScheme(securitySchemeName = "ifood-oauth", 
type = SecuritySchemeType.OAUTH2, 
flows = @OAuthFlows(password = 
@OAuthFlow(
    tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;

    @Inject
    PratoMapper pratoMapper;

    @GET
    public List<RestauranteDTO> buscar() {
        Stream<Restaurante> restaurantes = Restaurante.streamAll();
        return restaurantes.map(r -> restauranteMapper.toRestauranteDTO(r))
                .collect(Collectors.toList());
    }

    @POST
    @Transactional
    @APIResponse(responseCode = "400", content = 
    @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    @APIResponse(responseCode = "201", description ="Caso restaurante seja cadastrado com sucesso")
    public Response adicionar(@Valid AdicionarRestauranteDTO dto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restaurante.persist();
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, AtualizarRestauranteDTO dto) {
        Optional<Restaurante> restOptional = Restaurante.findByIdOptional(id);

        if (restOptional.isEmpty()) {
            throw new NotFoundException();
        }

        Restaurante atualRestaurante = restOptional.get();

        restauranteMapper.toRestaurante(dto, atualRestaurante);

        atualRestaurante.persist();

    }

    @DELETE
    @Transactional
    @Path("{id}")
    public void atualizar(@PathParam("id") Long id) {
        Optional<Restaurante> restOptional = Restaurante.findByIdOptional(id);

        restOptional.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException();
        });

    }

    // PRATO

    @GET
    @Path("{idRestaurante}/pratos")
    @Transactional
    @Tag(name = "Prato")
    public List<Restaurante> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {

        Optional<Restaurante> optRestaurante = Restaurante.findByIdOptional(idRestaurante);

        if (optRestaurante.isPresent()) {
            throw new NotFoundException("Restaurante não existe");
        }

        return Prato.list("restaurante", optRestaurante.get());

    }

    @POST
    @Path("{idRestaurante}/pratos")
    @Transactional
    @Tag(name = "Prato")
    public Response adicionar(@PathParam("idRestaurante") Long idRestaurante, AdicionarPratoDTO dto) {

        Optional<Restaurante> optRestaurante = Restaurante.findByIdOptional(idRestaurante);

        if (optRestaurante.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }

        Prato prato = pratoMapper.toPratoDTO(dto);
        prato.restaurante = optRestaurante.get();
        prato.persist();

        return Response.status(Status.CREATED).build();

    }

    @PUT
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "Prato")
    public Response atualizar(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id,
            Prato dto) {
        Optional<Restaurante> optRestaurante = Restaurante.findByIdOptional(idRestaurante);
        if (optRestaurante.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }
        Optional<Prato> optPrato = Prato.findByIdOptional(id);

        if (optPrato.isEmpty()) {
            throw new NotFoundException("Prato não existe");
        }
        Prato prato = optPrato.get();
        prato.preco = dto.preco;
        prato.persist();
        return Response.status(Status.CREATED).build();

    }

    @DELETE
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "Prato")
    public void delete(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id,
            Prato dto) {
        Optional<Restaurante> optRestaurante = Restaurante.findByIdOptional(idRestaurante);
        if (optRestaurante.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }
        Optional<Prato> optPrato = Prato.findByIdOptional(id);

        optPrato.ifPresentOrElse(Prato::delete, () -> {
            throw new NotFoundException();
        });

    }

}
