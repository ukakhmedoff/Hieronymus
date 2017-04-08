package ru.snatcher.generator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreenDaoGenerator {

	private static final int VERSION = 1;
	private static final String PACKAGE_DESTINATION = "ru.snatcher.hieronymus.db";
	private static final String DESTINATION_FOLDER = "./app/src/main/java";

	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(VERSION, PACKAGE_DESTINATION);

		Entity lvTranslateEntity = createTranslateEntity(schema);

		Entity lvLanguageEntity = createLanguageEntity(schema);

		new DaoGenerator().generateAll(schema, DESTINATION_FOLDER);
	}

	private static Entity createLanguageEntity(final Schema pSchema) {
		Entity lvLanguageEntity = pSchema.addEntity("Language");

		lvLanguageEntity.addIdProperty().primaryKey().autoincrement();
		lvLanguageEntity.addStringProperty("fLangKey").unique();
		lvLanguageEntity.addStringProperty("fLangValue").unique();

		return lvLanguageEntity;
	}

	private static Entity createTranslateEntity(Schema pSchema) {
		Entity lvTranslateEntity = pSchema.addEntity("Translate");

		lvTranslateEntity.addIdProperty().primaryKey().autoincrement();
		lvTranslateEntity.addStringProperty("fLang");
		lvTranslateEntity.addStringProperty("fTranslatedText").unique();
		lvTranslateEntity.addBooleanProperty("isBookmark");

		return lvTranslateEntity;
	}
}
