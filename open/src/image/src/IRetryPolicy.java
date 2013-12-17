import java.util.List;

public interface IRetryPolicy {

    public IRequest getNextRequest(List<IRequest> requests);
}
