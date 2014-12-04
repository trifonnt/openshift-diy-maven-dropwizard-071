package bg.d3soft.dropwizard.example.resources;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import bg.d3soft.dropwizard.example.model.Blog;

@Path("/")
public class IndexResource {

	@GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Timed
    public List<Blog> index() {
        return Arrays.asList(new Blog("Day 12: OpenCV--Face Detection for Java Developers", "https://www.openshift.com/blogs/day-12-opencv-face-detection-for-java-developers"));
    }
}