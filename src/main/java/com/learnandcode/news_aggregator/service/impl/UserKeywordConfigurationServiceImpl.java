package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.dto.KeywordConfigurationDTO;
import com.learnandcode.news_aggregator.exception.KeywordAlreadyExistsException;
import com.learnandcode.news_aggregator.exception.KeywordNotFoundException;
import com.learnandcode.news_aggregator.exception.UserNotFoundException;
import com.learnandcode.news_aggregator.model.NotificationConfigurationStatus;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.model.UserKeywordConfiguration;
import com.learnandcode.news_aggregator.repositories.UserKeywordConfigurationRepository;
import com.learnandcode.news_aggregator.repositories.UserRepository;
import com.learnandcode.news_aggregator.service.UserKeywordConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserKeywordConfigurationServiceImpl implements UserKeywordConfigurationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserKeywordConfigurationRepository userKeywordConfigurationRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserKeywordConfigurationServiceImpl.class);
    @Override
    public List<KeywordConfigurationDTO> getUserKeywordConfigurations() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);

        if (userOpted.isPresent()) {
            List<UserKeywordConfiguration> userKeywordConfigurationList =  userKeywordConfigurationRepository.findAllByUser(userOpted.get());
            if (userKeywordConfigurationList.isEmpty()) {
                logger.info("No keyword configurations found for user: {}", userName);
                throw new KeywordNotFoundException("No keyword configured for the user. Add keywords to get started.");
            }else {
                return userKeywordConfigurationList.stream()
                        .map(config -> new KeywordConfigurationDTO(
                                config.getKeyword(),
                                config.getKeywordConfigurationStatus().toString()))
                        .toList();
            }
        } else {
            logger.error("User with username {} not found", userName);
            throw new UserNotFoundException("User with the given username does not exist " + userName);
        }
    }


    @Override
    public void editKeywordConfiguration(String keyword) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);
        if(userOpted.isEmpty()){
            logger.error("User with username {} not found while editing keyword configuration", userName);
            throw new UserNotFoundException("User with the given username does not exist "+ userName);
        }
        Optional<UserKeywordConfiguration> configurationOpt = userKeywordConfigurationRepository.findByUserAndKeywordIgnoreCase(userOpted.get(), keyword);
        if(configurationOpt.isPresent()){
            UserKeywordConfiguration configuration = configurationOpt.get();
            NotificationConfigurationStatus currentStatus = configuration.getKeywordConfigurationStatus();

            configuration.setKeywordConfigurationStatus(
                    currentStatus == NotificationConfigurationStatus.ENABLED
                            ? NotificationConfigurationStatus.DISABLED
                            : NotificationConfigurationStatus.ENABLED
            );
            userKeywordConfigurationRepository.save(configuration);
        } else {
            logger.error("Keyword {} not found for user {}", keyword, userName);
            throw new KeywordNotFoundException("Keyword: " + keyword + " not found." + "Add it first to edit.");
        }
    }

    public void addKeywordConfiguration(String keyword) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);
        if(!userOpted.isPresent()){
            logger.error("User with username {} not found while adding keyword configuration", userName);
            throw new UserNotFoundException("User with the given username does not exist "+ userName);
        }

        boolean exists = userKeywordConfigurationRepository.existsByUserAndKeywordIgnoreCase(userOpted.get(), keyword);
        if(exists){
            logger.error("Keyword {} already exists for user {}", keyword, userName);
            throw new KeywordAlreadyExistsException("Keyword: " + keyword + " configuration already exists for this user");
        }

        UserKeywordConfiguration configuration = new UserKeywordConfiguration();
        configuration.setUser(userOpted.get());
        configuration.setKeyword(keyword.trim());
        configuration.setKeywordConfigurationStatus(NotificationConfigurationStatus.ENABLED);
        userKeywordConfigurationRepository.save(configuration);
    }
}
