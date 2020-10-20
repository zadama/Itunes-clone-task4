package com.example.itunesclone.controllers;

import com.example.itunesclone.data_access.ArtistRepository;
import com.example.itunesclone.data_access.GenreRepository;
import com.example.itunesclone.data_access.TrackRepository;
import com.example.itunesclone.models.Search;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeViewController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model){

        model.addAttribute("artists", ArtistRepository.getRandomArtists(5));
        model.addAttribute("tracks", TrackRepository.getRandomTracks(5));
        model.addAttribute("genres", GenreRepository.getRandomGenres(5));

        model.addAttribute("search", new Search());
        return "Index";
    }
}
