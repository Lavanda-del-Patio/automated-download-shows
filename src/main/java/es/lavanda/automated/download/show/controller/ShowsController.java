package es.lavanda.automated.download.show.controller;

import java.util.List;

import es.lavanda.automated.download.show.models.ShowModel;
import es.lavanda.automated.download.show.service.ShowsServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/feed-shows")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = { "http://localhost:4200", "https://lavandadelpatio.es",
		"https://pre.lavandadelpatio.es" }, allowCredentials = "true", exposedHeaders = "*", methods = {
				RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.PATCH, RequestMethod.POST,
				RequestMethod.PUT }, originPatterns = {})
public class ShowsController {

	private final ShowsServiceImpl showsServiceImpl;

	@GetMapping
	public ResponseEntity<Page<ShowModel>> getAllShows(Pageable pageable) {
		return ResponseEntity.ok(showsServiceImpl.getAllShowsOrderedByCreated(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShowModel> getShowAutomated(@PathVariable String id) {
		return ResponseEntity.ok(showsServiceImpl.getShow(id));
	}

	@GetMapping("/last")
	public ResponseEntity<List<ShowModel>> getLastShows() {
		return ResponseEntity.ok(showsServiceImpl.getLastShows());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShow(@PathVariable String id) {
		showsServiceImpl.deleteShow(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ShowModel> editShow(@RequestBody ShowModel showModel) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(showsServiceImpl.updateShow(showModel));
	}

	@GetMapping("/update-library")
	public ResponseEntity<Void> updateLibrary() {
		showsServiceImpl.updateLibrary();
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
