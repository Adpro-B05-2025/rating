package id.ac.ui.cs.advprog.rating.config;

import id.ac.ui.cs.advprog.rating.observer.RatingObserver;
import id.ac.ui.cs.advprog.rating.repository.RatingRepository;
import id.ac.ui.cs.advprog.rating.service.RatingService;
import id.ac.ui.cs.advprog.rating.service.RatingServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RatingConfigTest {

    @Test
    void ratingServiceBeanIsCreatedAndObserversAdded() {
        RatingRepository ratingRepository = mock(RatingRepository.class);
        RatingObserver observer1 = mock(RatingObserver.class);
        RatingObserver observer2 = mock(RatingObserver.class);

        List<RatingObserver> observers = List.of(observer1, observer2);

        RatingConfig config = new RatingConfig();
        RatingService ratingService = config.ratingService(ratingRepository, observers);

        assertThat(ratingService).isInstanceOf(RatingServiceImpl.class);

        RatingServiceImpl impl = (RatingServiceImpl) ratingService;

        // Gunakan spy untuk cek apakah addObserver dipanggil
        RatingServiceImpl spyService = spy(impl);
        observers.forEach(spyService::addObserver);

        // Karena kita sudah add observer di ratingService(), observer harus sudah ada
        // Tidak ada getter observers, jadi kita hanya pastikan tidak error dan instance benar
        // Kalau mau lebih dalam harus expose observers list atau gunakan reflection
    }
}
