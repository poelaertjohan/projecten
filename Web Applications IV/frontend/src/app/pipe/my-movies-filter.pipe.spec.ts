import { MyMoviesFilterPipe } from './my-movies-filter.pipe';

describe('MyMoviesFilterPipe', () => {
  it('create an instance', () => {
    const pipe = new MyMoviesFilterPipe();
    expect(pipe).toBeTruthy();
  });
});
