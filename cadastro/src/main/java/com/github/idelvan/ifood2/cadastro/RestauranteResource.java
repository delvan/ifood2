package com.github.idelvan.ifood2.cadastro;

import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.idelvan.ifood2.cadastro.dto.AdicionarRestauranteDTO;
import com.github.idelvan.ifood2.cadastro.dto.RestauranteMapper;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;

    @GET
    public List<Restaurante> ListarRestaurante() {
        return Restaurante.listAll();
    }

    @POST
    @Transactional
    public Response adicionar(AdicionarRestauranteDTO dto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restaurante.persist();
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, Restaurante dto) {
        Optional<Restaurante> restOptional = Restaurante.findByIdOptional(id);

        if (restOptional.isEmpty()) {
            throw new NotFoundException();
        }

        Restaurante atualRestaurante = restOptional.get();

        atualRestaurante.nome = dto.nome;

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
    public Response adicionar(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {

        Optional<Restaurante> optRestaurante = Restaurante.findByIdOptional(idRestaurante);

        if (optRestaurante.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }

        Prato prato = new Prato();

        prato.nome = dto.nome;
        prato.preco = dto.preco;
        prato.descricao = dto.descricao;
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
