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

		createTranslateEntity(schema);
		createLanguageEntity(schema);

		new DaoGenerator().generateAll(schema, DESTINATION_FOLDER);
	}

	private static Entity createLanguageEntity(final Schema pSchema) {
		Entity lvLanguageEntity = pSchema.addEntity("Language");

		lvLanguageEntity.addStringProperty("LangKey").unique().primaryKey();
		lvLanguageEntity.addStringProperty("LangValue");

		return lvLanguageEntity;
	}

	private static Entity createTranslateEntity(Schema pSchema) {
		Entity lvTranslateEntity = pSchema.addEntity("Translate");

		lvTranslateEntity.addStringProperty("Lang");
		lvTranslateEntity.addStringProperty("TranslatedText").primaryKey().unique();
		lvTranslateEntity.addBooleanProperty("isBookmark");

		return lvTranslateEntity;
	}
}
