package com.stockbit.aggregatorservice.audit;

import com.stockbit.aggregatorcore.user.entity.User;
import com.stockbit.aggregatorservice.config.ApplicationTestConfiguration;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.truth.Truth.assertThat;

@SpringBootTest(classes = ApplicationTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JPAAuditingConfigurationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditorAware<String> auditorProvider;

    @Test
    @Transactional
    void givenEntity_whenSave_shouldSaveWithAudit() {
        User user = new EasyRandom().nextObject(User.class);
        userRepository.saveAndFlush(user);
        User savedUser = userRepository.getOne(user.getId());
        String auditor = auditorProvider.getCurrentAuditor().orElseThrow();
        assertThat(savedUser.getCreatedBy()).isEqualTo(auditor);
        assertThat(savedUser.getCreatedTime()).isNotNull();
        assertThat(savedUser.getLastModifiedBy()).isEqualTo(auditor);
        assertThat(savedUser.getLastModifiedTime()).isNotNull();
    }
}
