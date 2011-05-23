
public class SimpleDeserializerFactory implements UniversalDeserializerFactory {

	@Override
	public UniversalDeserializer getDeserializer() {
		return new UniversalDeserializer();
		
	}

}
