/* ══════════════════════════════════════════════════════════
   GALVANITE — INTERACTIONS & ANIMATIONS
   ══════════════════════════════════════════════════════════ */

/* ── CANVAS NEBULA + STAR FIELD ──────────────────────────── */
(function initNebula() {
  const canvas = document.getElementById('nebula');
  if (!canvas) return;
  const ctx = canvas.getContext('2d');
  let W, H, stars = [], animId;

  function resize() {
    W = canvas.width  = window.innerWidth;
    H = canvas.height = window.innerHeight;
  }

  function makeStars(n) {
    stars = [];
    for (let i = 0; i < n; i++) {
      stars.push({
        x: Math.random() * W,
        y: Math.random() * H,
        r: Math.random() * 1.4 + .2,
        op: Math.random(),
        speed: Math.random() * .004 + .001,
        phase: Math.random() * Math.PI * 2
      });
    }
  }

  function drawNebula(t) {
    ctx.clearRect(0, 0, W, H);

    // deep bg
    const bg = ctx.createLinearGradient(0, 0, 0, H);
    bg.addColorStop(0,   '#070d1a');
    bg.addColorStop(.5,  '#0b1120');
    bg.addColorStop(1,   '#0d1525');
    ctx.fillStyle = bg;
    ctx.fillRect(0, 0, W, H);

    // orange nebula (upper right)
    const g1 = ctx.createRadialGradient(W * .75, H * .25, 0, W * .75, H * .25, W * .45);
    g1.addColorStop(0,   'rgba(200,90,20,.13)');
    g1.addColorStop(.4,  'rgba(180,60,10,.07)');
    g1.addColorStop(1,   'rgba(0,0,0,0)');
    ctx.fillStyle = g1;
    ctx.fillRect(0, 0, W, H);

    // blue nebula (left-centre)
    const g2 = ctx.createRadialGradient(W * .2, H * .5, 0, W * .2, H * .5, W * .5);
    g2.addColorStop(0,   'rgba(20,80,200,.1)');
    g2.addColorStop(.5,  'rgba(10,40,120,.05)');
    g2.addColorStop(1,   'rgba(0,0,0,0)');
    ctx.fillStyle = g2;
    ctx.fillRect(0, 0, W, H);

    // pulsing deep glow bottom
    const pulse = .5 + .5 * Math.sin(t * .0008);
    const g3 = ctx.createRadialGradient(W*.5, H*.85, 0, W*.5, H*.85, W*.4);
    g3.addColorStop(0,   `rgba(30,60,160,${.06 * pulse})`);
    g3.addColorStop(1,   'rgba(0,0,0,0)');
    ctx.fillStyle = g3;
    ctx.fillRect(0, 0, W, H);

    // stars
    for (let s of stars) {
      const twinkle = .3 + .7 * (.5 + .5 * Math.sin(s.phase + t * s.speed));
      ctx.beginPath();
      ctx.arc(s.x, s.y, s.r, 0, Math.PI * 2);
      ctx.fillStyle = `rgba(200,230,255,${twinkle * s.op})`;
      ctx.fill();
    }
  }

  let last = 0;
  function loop(t) {
    if (t - last > 32) { drawNebula(t); last = t; }
    animId = requestAnimationFrame(loop);
  }

  resize();
  makeStars(160);
  loop(0);

  const ro = new ResizeObserver(() => { resize(); makeStars(160); });
  ro.observe(document.body);
})();

/* ── CURSOR GLOW ────────────────────────────────────────── */
(function initCursor() {
  const glow = document.getElementById('cursorGlow');
  if (!glow || window.matchMedia('(pointer:coarse)').matches) return;
  let mx = -999, my = -999, cx = -999, cy = -999;
  document.addEventListener('mousemove', e => {
    mx = e.clientX; my = e.clientY;
    glow.style.opacity = '1';
  });
  document.addEventListener('mouseleave', () => { glow.style.opacity = '0'; });
  function lerp(a, b, t) { return a + (b - a) * t; }
  (function track() {
    cx = lerp(cx, mx, .08);
    cy = lerp(cy, my, .08);
    glow.style.left = cx + 'px';
    glow.style.top  = cy + 'px';
    requestAnimationFrame(track);
  })();
})();

/* ── NAV SCROLL ─────────────────────────────────────────── */
const nav = document.getElementById('nav');
window.addEventListener('scroll', () => {
  nav.classList.toggle('scrolled', window.scrollY > 50);
}, { passive: true });

/* ── MOBILE BURGER ──────────────────────────────────────── */
const burger   = document.getElementById('navBurger');
const navLinks = document.getElementById('navLinks');

burger.addEventListener('click', () => {
  const open = burger.classList.toggle('open');
  navLinks.classList.toggle('open', open);
  document.body.style.overflow = open ? 'hidden' : '';
});
navLinks.querySelectorAll('a').forEach(a => {
  a.addEventListener('click', () => {
    burger.classList.remove('open');
    navLinks.classList.remove('open');
    document.body.style.overflow = '';
  });
});
// close on outside click
document.addEventListener('click', e => {
  if (navLinks.classList.contains('open') && !navLinks.contains(e.target) && !burger.contains(e.target)) {
    burger.classList.remove('open');
    navLinks.classList.remove('open');
    document.body.style.overflow = '';
  }
});

/* ── REVEAL ON SCROLL ───────────────────────────────────── */
(function initReveal() {
  const els = document.querySelectorAll('.reveal, .fade-up, .fade-in');
  const io = new IntersectionObserver((entries) => {
    entries.forEach((e, i) => {
      if (!e.isIntersecting) return;
      // stagger siblings: find index among visible siblings
      const siblings = [...e.target.parentElement.querySelectorAll('.reveal,.fade-up,.fade-in')];
      const idx = siblings.indexOf(e.target);
      const base = parseFloat(getComputedStyle(e.target).getPropertyValue('--d') || '0');
      const extra = idx * 0.07;
      e.target.style.transitionDelay = (base + extra) + 's';
      e.target.classList.add('visible');
      io.unobserve(e.target);
    });
  }, { threshold: 0.1, rootMargin: '0px 0px -50px 0px' });

  els.forEach(el => io.observe(el));
})();

/* ── CARD TILT (growth cards, case cards, t-cards) ───────── */
(function initTilt() {
  const cards = document.querySelectorAll('.growth-card, .case-card, .t-card, .launchpad-card');
  if (window.matchMedia('(pointer:coarse)').matches) return;

  cards.forEach(card => {
    card.addEventListener('mousemove', e => {
      const r = card.getBoundingClientRect();
      const x = (e.clientX - r.left) / r.width  - .5;
      const y = (e.clientY - r.top)  / r.height - .5;
      card.style.transform = `perspective(800px) rotateY(${x * 6}deg) rotateX(${-y * 6}deg) translateY(-4px)`;
    });
    card.addEventListener('mouseleave', () => {
      card.style.transform = '';
    });
  });
})();

/* ── FAQ ACCORDION ──────────────────────────────────────── */
document.querySelectorAll('.faq-item').forEach(item => {
  item.addEventListener('toggle', () => {
    if (item.open) {
      document.querySelectorAll('.faq-item[open]').forEach(other => {
        if (other !== item) other.removeAttribute('open');
      });
    }
  });
});

/* ── MARQUEE PAUSE ON HOVER ─────────────────────────────── */
const marqueeTrack = document.querySelector('.marquee-track');
if (marqueeTrack) {
  const strip = marqueeTrack.parentElement;
  strip.addEventListener('mouseenter', () => { marqueeTrack.style.animationPlayState = 'paused'; });
  strip.addEventListener('mouseleave', () => { marqueeTrack.style.animationPlayState = 'running'; });
}

/* ── SMOOTH SCROLL for anchor links ────────────────────── */
document.querySelectorAll('a[href^="#"]').forEach(a => {
  a.addEventListener('click', e => {
    const target = document.querySelector(a.getAttribute('href'));
    if (!target) return;
    e.preventDefault();
    const top = target.getBoundingClientRect().top + window.scrollY - 80;
    window.scrollTo({ top, behavior: 'smooth' });
  });
});

/* ── NUMBER COUNT-UP for float stats ────────────────────── */
(function initCountUp() {
  const stats = document.querySelectorAll('.stat-val');
  const io = new IntersectionObserver(entries => {
    entries.forEach(e => {
      if (!e.isIntersecting) return;
      const el = e.target;
      const raw = el.textContent.trim();
      // parse: handle "4 wks", "+180%", etc.
      const match = raw.match(/^([+\-]?)(\d+(?:\.\d+)?)(.*)$/);
      if (!match) return;
      const [, prefix, numStr, suffix] = match;
      const target = parseFloat(numStr);
      const isInt = !numStr.includes('.');
      let start = 0;
      const dur = 1600;
      const t0 = performance.now();
      function tick(now) {
        const p = Math.min((now - t0) / dur, 1);
        const ease = 1 - Math.pow(1 - p, 3);
        const val = ease * target;
        el.textContent = prefix + (isInt ? Math.round(val) : val.toFixed(1)) + suffix;
        if (p < 1) requestAnimationFrame(tick);
      }
      requestAnimationFrame(tick);
      io.unobserve(el);
    });
  }, { threshold: .5 });
  stats.forEach(s => io.observe(s));
})();
