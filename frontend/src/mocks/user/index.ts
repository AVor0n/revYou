import { HttpResponse, delay, http } from 'msw';
import { type MockUser, initialUsers } from './initial';
import type { SignInRequest, SignInResponse, SignUpRequest, User } from '@api';

let userIdCounter = initialUsers.length + 1;
const users = new Map(initialUsers.map(user => [user.username, user]));

export const usersHandlers = [
  http.post<Record<string, never>, SignInRequest, SignInResponse | { message: string }>(
    '/api/auth/sign-in',
    async ({ request }) => {
      const userData = await request.json();
      if (!userData.username || !userData.password) {
        return HttpResponse.json({ message: 'wrong requestData' }, { status: 400 });
      }

      const user = users.get(userData.username);
      if (!user) {
        return HttpResponse.json({ message: 'no such user' }, { status: 404 });
      }

      if (user.password !== userData.password) {
        return HttpResponse.json({ message: 'wrong password' }, { status: 403 });
      }

      return HttpResponse.json(user);
    },
  ),
  http.post<Record<string, never>, SignUpRequest>('/api/auth/sign-up', async ({ request }) => {
    await delay(300);
    const userData = await request.json();
    if (!userData.username || !userData.password || !userData.email) {
      return HttpResponse.json({ message: 'wrong requestData' }, { status: 400 });
    }

    if (users.has(userData.username)) {
      return HttpResponse.json({ message: 'user already exist' }, { status: 400 });
    }

    const id = userIdCounter;
    userIdCounter += 1;
    const user: MockUser = {
      email: userData.email,
      password: userData.password,
      accessToken: `token-${id}`,
      refreshToken: `token-${id}`,
      userId: id,
      role: 'STUDENT',
      username: userData.username,
    };
    users.set(userData.username, user);

    return HttpResponse.json({
      userId: user.userId,
      username: user.username,
      email: user.email,
    } satisfies User);
  }),
];
