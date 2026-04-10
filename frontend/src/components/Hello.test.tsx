import React from 'react';
import { render, screen } from '@testing-library/react';
import Hello from './Hello';

test('renders the heading', () => {
  render(<Hello message="" />);
  expect(screen.getByText('Backend Response:')).toBeInTheDocument();
});

test('shows default text when message is empty', () => {
  render(<Hello message="" />);
  expect(screen.getByText('Click the button to fetch a message!')).toBeInTheDocument();
});

test('displays the provided message', () => {
  render(<Hello message="Hello World" />);
  expect(screen.getByText('Hello World')).toBeInTheDocument();
});
