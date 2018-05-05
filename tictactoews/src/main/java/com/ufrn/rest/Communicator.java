package com.ufrn.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/play")
public class Communicator {
	
	@GET
	@Path("/{option}")
	public Response getMsg(@PathParam("option") String msg,
			@QueryParam("linha") int linha,
			@QueryParam("coluna") int coluna) {
 
		String output = "Option is : L" + linha + ", C" + coluna;
		output = "<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<body>\r\n" + 
				"\r\n" + 
				"<h2> Option is : L" + linha + ", C" + coluna + "</h2>\r\n" + 
				"<img src=\"https://i1.wp.com/www.gpabrasil.com.br/wp-content/uploads/2018/03/melhor-amigo-do-homem.jpg?w=640&ssl=1\" alt=\"Flowers in Chania\" width=\"460\" height=\"345\">\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"</html>";
		
		return Response.status(200).entity(output).build();
 
	}

}
