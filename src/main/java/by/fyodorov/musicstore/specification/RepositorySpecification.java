package by.fyodorov.musicstore.specification;

public interface RepositorySpecification {
    String toSqlClauses();
    String[] getArguments();
}
