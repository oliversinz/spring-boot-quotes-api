package spring.quotes.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.quotes.api.author.Author;
import spring.quotes.api.category.Category;
import spring.quotes.api.quote.Quote;
import spring.quotes.api.author.AuthorRepository;
import spring.quotes.api.category.CategoryRepository;
import spring.quotes.api.quote.QuoteRepository;
import spring.quotes.api.user.AppUser;
import spring.quotes.api.user.AppUserRole;
import spring.quotes.api.user.AppUserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        AppUser user1 = new AppUser("Oliver", "Sinz", "oliversinz@gmail.com", "$2a$10$umy3C6RV3Ukiwn5mminlF.ySc/QGaRbf5jkq/.9z0vGELKiDqgRjS", AppUserRole.ADMIN);
        userRepository.save(user1);

        AppUser user2 = new AppUser("Anucha", "Thamnao", "anucha@gmail.com", "$2a$10$umy3C6RV3Ukiwn5mminlF.ySc/QGaRbf5jkq/.9z0vGELKiDqgRjS", AppUserRole.USER);
        userRepository.save(user2);

        String csvFilePath = "C:\\Users\\chakran\\Documents\\quotes.csv";

        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(";");
                String quoteContent = data[0];
                String quoteAuthor = data[1];
                String quoteCategory = data.length == 3 ? data[2] : "";

                Quote quote = new Quote(quoteContent);
                quoteRepository.save(quote);

                if (!authorRepository.existsByAuthorName(quoteAuthor)) {
                    Author author = new Author(quoteAuthor);
                    authorRepository.save(author);
                    author.addQuote(quote);
                    quoteRepository.save(quote);
                } else {
                    Author author = authorRepository.findByAuthorName(quoteAuthor);
                    authorRepository.save(author);
                    author.addQuote(quote);
                    quoteRepository.save(quote);
                }

                if (!categoryRepository.existsByCategoryName(quoteCategory)) {
                    Category category = new Category(quoteCategory);
                    categoryRepository.save(category);
                    quote.addCategory(category);
                    quoteRepository.save(quote);
                } else {
                    Category category = categoryRepository.findByCategoryName(quoteCategory);
                    categoryRepository.save(category);
                    quote.addCategory(category);
                    quoteRepository.save(quote);
                }

            }

            lineReader.close();

        } catch (IOException ex) {
            System.err.println(ex);
        }

//        Quote quote = new Quote("The only real valuable thing is intuition.");
//        quoteRepository.save(quote);
//
//        Author author = new Author("Albert Einstein", "Albert Einstein was a German-born theoretical physicist.");
//        authorRepository.save(author);
//
//        author.addQuote(quote);
//
//        quoteRepository.save(quote);
//
//        Category category = new Category("Wisdom", "All around wisdom");
//        categoryRepository.save(category);
//
//        quote.addCategory(category);
//
//        quoteRepository.save(quote);
    }

}
