package com.example.demo;

import com.example.demo.Repositories.UserRepository;
import com.example.demo.managers.UserDaoService;
import com.example.demo.models.User;
import com.example.demo.dtos.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class DemoApplicationTests {

    @Qualifier("dbServer1")
    @Autowired
    private DataSource db1;

    @Test
    public void testProperties() {
    }

    @Test
    public void mocktest() {
        UserRepository repo = mock(UserRepository.class);
        when(repo.getUsers()).thenReturn(new ArrayList<User>() {{
            add(new User("jeff", "test1@gmail.com"));
            add(new User("Samon", "samon@hotmail.com"));
        }});

        UserDaoService manager = new UserDaoService(repo);
        List<UserDto> result = manager.getUsers();
        assertTrue(result != null && result.size() == 2);
        assertTrue(result.get(0).getName() == "jeff" && result.get(0).getEmail() == "test1@gmail.com");
    }

}
