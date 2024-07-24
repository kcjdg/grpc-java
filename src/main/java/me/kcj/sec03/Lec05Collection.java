package me.kcj.sec03;

import me.kcj.models.sec03.Address;
import me.kcj.models.sec03.Book;
import me.kcj.models.sec03.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Lec05Collection {

    private static final Logger log = LoggerFactory.getLogger(Lec05Collection.class);


    public static void main(String[] args) {
        final var book1 = Book.newBuilder()
                .setTitle("Harry potter - part 1")
                .setAuthor("JK Rowling")
                .setPublicationYear(1997)
                .build();

        final var book2 = book1.toBuilder()
                .setTitle("Harry potter - part 2")
                .setAuthor("JK Rowling")
                .setPublicationYear(1998)
                .build();


        final var book3 = book1.toBuilder()
                .setTitle("Harry potter - part 3")
                .setAuthor("JK Rowling")
                .setPublicationYear(1999)
                .build();

        final var library = Library.newBuilder()
                .setName("Fantasy Library")
                .addAllBooks(List.of(book1,book2, book3))
                .build();

        log.info("{}", library);


    }
}
