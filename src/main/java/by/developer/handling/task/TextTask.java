package by.developer.handling.task;

import by.developer.handling.model.CompositeTextComponent;

public interface TextTask<R> {
  R execute(CompositeTextComponent text);
}
