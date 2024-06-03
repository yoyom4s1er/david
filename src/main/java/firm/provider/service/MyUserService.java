package firm.provider.service;

import firm.provider.exception.WrongFieldsRegisterException;
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

        if (user.getFirstName() == null ||
                user.getLastName() == null ||
                user.getThirdName() == null) {
            throw new WrongFieldsRegisterException("Имя, Фамилия, Отчество должно содержать хотя бы 1 символ");
        }

        if (user.getFirstName().isEmpty() ||
                user.getLastName().isEmpty() ||
                user.getThirdName().isEmpty()) {
            throw new WrongFieldsRegisterException("Имя, Фамилия, Отчество должно содержать хотя бы 1 символ");
        }

        if (!user.getFirstName().matches("[А-ЯЁа-яё]+") ||
                !user.getLastName().matches("[А-ЯЁа-яё]+") ||
                !user.getThirdName().matches("[А-ЯЁа-яё]+")) {
            throw new WrongFieldsRegisterException("Имя, Фамилия, Отчество должны состоять только из кириллицы");
        }

        if (user.getPassword().length() < 4) {
            throw new WrongFieldsRegisterException("Пароль должен состоять минимум из 4 символов");

        }

        if (!user.getMail().contains("@")) {
            throw new WrongFieldsRegisterException("Почта должна содержать символ \"@\"");
        }

        if (user.getMail().indexOf("@") == 0 || user.getMail().indexOf("@") == user.getMail().length() - 1) {
            throw new WrongFieldsRegisterException("Почта должна иметь символы до @ и после @");
        }

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
