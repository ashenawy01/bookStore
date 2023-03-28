package com.bookStore.controller;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    MyBookService myBookService;

    @GetMapping("/")
    public String home (){
        return "home";
    }

    @GetMapping("/book_register")
    public String bookRegister () {
        return "book_register";
    }
    @GetMapping("/available_books")
    public ModelAndView availableBooks () {
        List<Book> books = bookService.getAllBook();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bookList");
        modelAndView.addObject("book", books);
        return modelAndView;
    }
    @GetMapping("/my_books")
    public ModelAndView myBooks () {
        List<MyBookList> myBookLists = myBookService.getAllMyBooks();
        return new ModelAndView("myBooks", "book", myBookLists);
    }

    @PostMapping("/save")
    public String addBook (@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/available_books";
    }
    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        Book book = bookService.getBookById(id);
        MyBookList myBookList = new MyBookList(book.getId(), book.getName(), book.getAuthor(), book.getPrice());
        myBookService.saveMyBooks(myBookList);
        return "redirect:/my_books";
    }
    @RequestMapping("/editBook/{id}")
    public String editBook (@PathVariable("id") int id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "bookEdit";
    }
    @RequestMapping("/deleteBook/{id}")
    public String deleteMyList (@PathVariable("id") int id){
        bookService.deleteById(id);
        return "redirect:/available_books";
    }
}
