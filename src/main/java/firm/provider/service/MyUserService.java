package firm.provider.service;

import firm.provider.model.MyUser;
import firm.provider.repository.UserRepository;
import firm.provider.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<MyUser> getUser(String mail) {
        return userRepository.findUserByMailIs(mail);
    }
    public void addUser(MyUser user) {
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void completeTask1(String mail) {
        MyUser user = userRepository.findUserByMailIs(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with mail: " + mail + " not found"))
                ;
        user.setTask1Completed(true);
        userRepository.save(user);
    }

    public void addMistakeToTask1(String mail) {
        MyUser user = userRepository.findUserByMailIs(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with mail: " + mail + " not found"))
                ;
        user.setTask1Mistakes(user.getTask1Mistakes() + 1);
        userRepository.save(user);
    }

    public void completeTask2(String mail) {
        MyUser user = userRepository.findUserByMailIs(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with mail: " + mail + " not found"))
                ;
        user.setTask2Completed(true);
        userRepository.save(user);
    }

    public void addMistakeToTask2(String mail) {
        MyUser user = userRepository.findUserByMailIs(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with mail: " + mail + " not found"))
                ;
        user.setTask2Mistakes(user.getTask2Mistakes() + 1);
        userRepository.save(user);
    }

    public void completeTask3(String mail) {
        MyUser user = userRepository.findUserByMailIs(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with mail: " + mail + " not found"))
                ;
        user.setTask3Completed(true);
        userRepository.save(user);
    }

    public void addMistakeToTask3(String mail) {
        MyUser user = userRepository.findUserByMailIs(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with mail: " + mail + " not found"))
                ;
        user.setTask3Mistakes(user.getTask3Mistakes() + 1);
        userRepository.save(user);
    }

    public void completeTask4(String mail, int mistakesCount) {
        MyUser user = userRepository.findUserByMailIs(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with mail: " + mail + " not found"))
                ;
        user.setTask4Mistakes(mistakesCount);
        user.setTask4Completed(true);
        userRepository.save(user);
    }
}
