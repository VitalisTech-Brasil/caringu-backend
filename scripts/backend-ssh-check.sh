#!/bin/bash
set -euo pipefail

PROXY_HOST="${1:-}"
BACKEND1_HOST="${2:-}"
BACKEND2_HOST="${3:-}"
SSH_KEY_FILE="${4:-}"
SSH_USER="ubuntu"

if [[ -z "$PROXY_HOST" || -z "$SSH_KEY_FILE" ]]; then
  echo "Uso: $0 <PROXY_HOST> <BACKEND1_HOST> <BACKEND2_HOST> <SSH_KEY_FILE>"
  exit 1
fi

echo "🔍 Testando conexão SSH com o jump-host (Proxy: $PROXY_HOST)..."
ssh -i "$SSH_KEY_FILE" -o StrictHostKeyChecking=no "$SSH_USER@$PROXY_HOST" "echo OK" || {
  echo "❌ Falha ao conectar na Proxy EC2 ($PROXY_HOST)"
  exit 1
}

test_target() {
  local target_host="$1"
  [[ -z "$target_host" ]] && return 0

  echo "🔍 Testando conexão SSH Proxy → Target ($target_host)..."
  ssh -i "$SSH_KEY_FILE" -o StrictHostKeyChecking=no -J "$SSH_USER@$PROXY_HOST" "$SSH_USER@$target_host" "echo OK" || {
    echo "❌ Falha ao conectar (Proxy → $target_host)"
    exit 1
  }
}

test_target "$BACKEND1_HOST"
test_target "$BACKEND2_HOST"

echo "✅ Conectividade OK entre Proxy e Backends"
echo "⚠️  ATENÇÃO: Não esqueça de ligar o NAT Gateway antes do deploy!"


