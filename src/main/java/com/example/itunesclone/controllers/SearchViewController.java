package com.example.itunesclone.controllers;

import com.example.itunesclone.data_access.SearchRepository;
import com.example.itunesclone.models.Search;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchViewController {

    /* When a search request is made at home page, the results are displayed at search page. */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String getSearch(Search search, Model model) {
        model.addAttribute("query", search.getQuery());
        model.addAttribute("results", SearchRepository.getSearchedResult(search.getQuery()));

        return "search";
    }
}
