import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import App from './App';

beforeEach(() => {
  jest.restoreAllMocks();
});

test('renders heading and button', () => {
  render(<App />);
  expect(screen.getByText('Web App')).toBeInTheDocument();
  expect(screen.getByText('Get Message from Backend')).toBeInTheDocument();
});

test('shows default prompt before fetching', () => {
  render(<App />);
  expect(screen.getByText('Click the button to fetch a message!')).toBeInTheDocument();
});

test('fetches and displays backend message on button click', async () => {
  jest.spyOn(global, 'fetch').mockResolvedValueOnce({
    text: async () => 'Hello from backend!',
  } as Response);

  render(<App />);
  fireEvent.click(screen.getByText('Get Message from Backend'));

  await waitFor(() => {
    expect(screen.getByText('Hello from backend!')).toBeInTheDocument();
  });

  expect(global.fetch).toHaveBeenCalledWith(
    expect.stringContaining('/hello')
  );
});

test('displays error message when fetch fails', async () => {
  jest.spyOn(global, 'fetch').mockRejectedValueOnce(new Error('Network error'));
  jest.spyOn(console, 'error').mockImplementation(() => {});

  render(<App />);
  fireEvent.click(screen.getByText('Get Message from Backend'));

  await waitFor(() => {
    expect(screen.getByText('Error fetching message')).toBeInTheDocument();
  });
});
