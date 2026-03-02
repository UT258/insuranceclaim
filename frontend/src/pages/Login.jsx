import { useState } from 'react'
import { identityAPI } from '../api'

export default function Login({ onLogin }) {
  const [mode, setMode] = useState('signin')
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [phone, setPhone] = useState('')
  const [role, setRole] = useState('ANALYST')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const handleSubmit = async (event) => {
    event.preventDefault()
    setLoading(true)
    setError('')

    try {
      if (mode === 'signup') {
        const createResponse = await identityAPI.createUser({
          name: name.trim(),
          email: email.trim(),
          phone: phone.trim() || null,
          role
        })

        const createdUser = createResponse.data
        onLogin({
          userId: createdUser.userId,
          name: createdUser.name,
          email: createdUser.email,
          role: createdUser.role
        })
      } else {
        const response = await identityAPI.getUsers()
        const users = response.data || []
        const matchedUser = users.find((user) =>
          (user.email || '').toLowerCase() === email.trim().toLowerCase()
        )

        if (!matchedUser) {
          setError('No user found with this email. Please register first.')
          return
        }

        onLogin({
          userId: matchedUser.userId,
          name: matchedUser.name,
          email: matchedUser.email,
          role: matchedUser.role
        })
      }
    } catch (requestError) {
      if (requestError.response?.status === 400) {
        setError('Invalid registration data. Check email format and required fields.')
      } else {
        setError('Unable to reach Identity Service. Please ensure backend services are running.')
      }
      console.error(requestError)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <h1>ClaimInsight360</h1>
        <p>{mode === 'signin' ? 'Sign in to continue' : 'Create account to get started'}</p>

        <div className="auth-switch">
          <button
            type="button"
            className={`btn ${mode === 'signin' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => {
              setMode('signin')
              setError('')
            }}
          >
            Sign in
          </button>
          <button
            type="button"
            className={`btn ${mode === 'signup' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => {
              setMode('signup')
              setError('')
            }}
          >
            Register
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          {mode === 'signup' && (
            <>
              <label htmlFor="name">Full Name</label>
              <input
                id="name"
                type="text"
                value={name}
                onChange={(event) => setName(event.target.value)}
                placeholder="Jane Doe"
                required
              />
            </>
          )}

          <label htmlFor="email">Work Email</label>
          <input
            id="email"
            type="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            placeholder="you@company.com"
            required
          />

          {mode === 'signup' && (
            <>
              <label htmlFor="phone">Phone (optional)</label>
              <input
                id="phone"
                type="text"
                value={phone}
                onChange={(event) => setPhone(event.target.value)}
                placeholder="+1 555 123 4567"
              />

              <label htmlFor="role">Role</label>
              <select
                id="role"
                value={role}
                onChange={(event) => setRole(event.target.value)}
              >
                <option value="ANALYST">ANALYST</option>
                <option value="MANAGER">MANAGER</option>
                <option value="FRAUD">FRAUD</option>
                <option value="ACTUARY">ACTUARY</option>
                <option value="EXECUTIVE">EXECUTIVE</option>
                <option value="ADMIN">ADMIN</option>
              </select>
            </>
          )}

          {error && <div className="login-error">{error}</div>}

          <button className="btn btn-primary login-btn" type="submit" disabled={loading}>
            {loading ? (mode === 'signin' ? 'Signing in...' : 'Registering...') : (mode === 'signin' ? 'Sign in' : 'Register')}
          </button>
        </form>
      </div>
    </div>
  )
}
