package ru.snatcher.hieronymus.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ru.snatcher.hieronymus.model.api.ApiTranslateInterface;
import ru.snatcher.hieronymus.model.entity.LanguageDTO;
import ru.snatcher.hieronymus.model.entity.TranslateDTO;
import ru.snatcher.hieronymus.other.BaseTest;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * {@link ModelImplTest}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class ModelImplTest extends BaseTest {

	@Inject
	protected ApiTranslateInterface apiInterface;

	private Model model;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		component.inject(this);
		model = new ModelImpl();
	}

	@Test
	public void testGetLanguages() {
		LanguageDTO[] repositoryDTOs = testUtils.getGson().fromJson(testUtils.readString("json/repos.json"), RepositoryDTO[].class);

		when(apiInterface.getLangs(TestConst.TEST_OWNER)).thenReturn(Observable.just(Arrays.asList(repositoryDTOs)));

		TestSubscriber<List<LanguageDTO>> testSubscriber = new TestSubscriber<>();
		model.getLangs(TestConst.TEST_OWNER).subscribe(testSubscriber);

		testSubscriber.assertNoErrors();
		testSubscriber.assertValueCount(1);

		List<LanguageDTO> actual = testSubscriber.getOnNextEvents().get(0);

		assertEquals(7, actual.size());
		assertEquals("Android-Rate", actual.get(0).getName());
		assertEquals("andrey7mel/Android-Rate", actual.get(0).getFullName());
		assertEquals(26314692, actual.get(0).getId());
	}


	@Test
	public void testGetTranslate() {
		TranslateDTO[] lvTranslateDTOs = testUtils.getGson().fromJson(testUtils.readString("json/contributors.json"), TranslateDTO[].class);

		when(apiInterface.getContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO)).thenReturn(Observable.just(Arrays.asList(lvTranslateDTOs)));


		TestSubscriber<List<TranslateDTO>> testSubscriber = new TestSubscriber<>();
		model.getRepoContributors(TestConst.TEST_OWNER, TestConst.TEST_REPO).subscribe(testSubscriber);

		testSubscriber.assertNoErrors();
		testSubscriber.assertValueCount(1);

		List<TranslateDTO> actual = testSubscriber.getOnNextEvents().get(0);

		assertEquals(11, actual.size());
		assertEquals("hotchemi", actual.get(0).getLogin());
		assertEquals("User", actual.get(0).getType());
		assertEquals(471318, actual.get(0).getId());

	}
}